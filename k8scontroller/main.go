package main

import (
    "context"
    "fmt"
    "os"
    "net/http"
    "bytes"
    "time"

    appsv1 "k8s.io/api/apps/v1"
    metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
    "k8s.io/apimachinery/pkg/watch"
    "k8s.io/client-go/kubernetes"
    "k8s.io/client-go/rest"
)

func main() {
    fmt.Println("Controller starting...")

    // Load config
    counterURL := os.Getenv("COUNTER_URL")
    if counterURL == "" {
        fmt.Println("COUNTER_URL not set. Exiting.")
        os.Exit(1)
    }

    // Set up Kubernetes client
    config, err := rest.InClusterConfig()
    if err != nil {
        panic(err.Error())
    }
    clientset, err := kubernetes.NewForConfig(config)
    if err != nil {
        panic(err.Error())
    }

    // Watch Deployments in all namespaces
    watcher, err := clientset.AppsV1().Deployments("").Watch(context.TODO(), metav1.ListOptions{})
    if err != nil {
        panic(err.Error())
    }

    ch := watcher.ResultChan()
    for event := range ch {
        deploy, ok := event.Object.(*appsv1.Deployment)
        if !ok {
            continue
        }

        labels := deploy.GetLabels()
        if labels["app"] != "nginx" {
            continue
        }

        switch event.Type {
        case watch.Added:
            go callCounter(counterURL + "/increment")
        case watch.Deleted:
            go callCounter(counterURL + "/decrement")
        }
    }
}

func callCounter(url string) {
    fmt.Printf("Calling counter: %s\n", url)
    req, err := http.NewRequest("POST", url, bytes.NewBuffer([]byte(`{}`)))
    if err != nil {
        fmt.Println("Error creating request:", err)
        return
    }

    client := &http.Client{Timeout: 5 * time.Second}
    resp, err := client.Do(req)
    if err != nil {
        fmt.Println("Error calling counter:", err)
        return
    }
    defer resp.Body.Close()
    fmt.Println("Counter call done. Status:", resp.StatusCode)
}
