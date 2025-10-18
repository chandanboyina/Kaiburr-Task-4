//package com.chandan.task;
//
//import org.springframework.stereotype.Service;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.Optional;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//
//@Service
//public class TaskService {
//
//    private final TaskRepository taskRepository;
//
//    public TaskService(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }
//
//    public List<Task> getAllTasks() {
//        return taskRepository.findAll();
//    }
//
//    public Optional<Task> getTaskById(String id) {
//        return taskRepository.findById(id);
//    }
//
//    public Task putTask(Task task) {
//        // Validation check for malicious code
//        if (isUnsafeCommand(task.getCommand())) {
//            throw new IllegalArgumentException("Unsafe command detected.");
//        }
//        return taskRepository.save(task);
//    }
//
//    public void deleteTask(String id) {
//        taskRepository.deleteById(id);
//    }
//
//    public List<Task> findTasksByName(String name) {
//        return taskRepository.findByNameContaining(name);
//    }
//
//    public Optional<Task> putTaskExecution(String taskId) {
//        return taskRepository.findById(taskId).map(task -> {
//            // Initialize the list outside the try block
//            if (task.getTaskExecutions() == null) {
//                task.setTaskExecutions(new ArrayList<>());
//            }
//
//            TaskExecution execution = new TaskExecution();
//            execution.setStartTime(new Date());
//
//            try {
//                // Corrected command for Windows
////                String[] command = {"cmd.exe", "/c", task.getCommand()};
////                Process process = Runtime.getRuntime().exec(command);
////                process.waitFor();
//                String[] command = {"/bin/sh", "-c", task.getCommand()};
//                Process process = Runtime.getRuntime().exec(command);
//                process.waitFor();
//
//                // Read the output
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                String output = reader.lines().collect(Collectors.joining("\n"));
//
//                execution.setEndTime(new Date());
//                execution.setOutput(output);
//
//            } catch (Exception e) {
//                // Handle exceptions
//                execution.setEndTime(new Date());
//                execution.setOutput("Execution failed: " + e.getMessage());
//            }
//
//            // Add the execution to the list
//            task.getTaskExecutions().add(execution);
//            taskRepository.save(task);
//
//            return task;
//        });
//    }
//
//    // A simple validation to prevent malicious commands.
//    private boolean isUnsafeCommand(String command) {
//        // A simple check, a real-world application would need a more robust security mechanism
//        return command != null && (command.contains("rm -rf") || command.contains("sudo") || command.contains("&"));
//    }
//}
//---------------------------------------------------------------//
//package com.chandan.task;
//
//import io.kubernetes.client.openapi.ApiClient;
//import io.kubernetes.client.openapi.ApiException;
//import io.kubernetes.client.openapi.Configuration;
//import io.kubernetes.client.openapi.apis.CoreV1Api;
//import io.kubernetes.client.openapi.models.V1Pod;
//import io.kubernetes.client.openapi.models.V1PodBuilder;
//import io.kubernetes.client.util.ClientBuilder;
//import org.springframework.stereotype.Service;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.Optional;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class TaskService {
//
//    private final TaskRepository taskRepository;
//
//    public TaskService(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }
//
//    public List<Task> getAllTasks() {
//        return taskRepository.findAll();
//    }
//
//    public Optional<Task> getTaskById(String id) {
//        return taskRepository.findById(id);
//    }
//
//    public Task putTask(Task task) {
//        // Validation check for malicious code
//        if (isUnsafeCommand(task.getCommand())) {
//            throw new IllegalArgumentException("Unsafe command detected.");
//        }
//        return taskRepository.save(task);
//    }
//
//    public void deleteTask(String id) {
//        taskRepository.deleteById(id);
//    }
//
//    public List<Task> findTasksByName(String name) {
//        return taskRepository.findByNameContaining(name);
//    }
//
//    public Optional<Task> putTaskExecution(String taskId) {
//        return taskRepository.findById(taskId).map(task -> {
//            // Initialize the list outside the try block
//            if (task.getTaskExecutions() == null) {
//                task.setTaskExecutions(new ArrayList<>());
//            }
//
//            TaskExecution execution = new TaskExecution();
//            execution.setStartTime(new Date());
//
//            try {
//                // Set up Kubernetes API client
//                ApiClient client = ClientBuilder.defaultClient();
//                Configuration.setDefaultApiClient(client);
//                CoreV1Api api = new CoreV1Api();
//
//                String podName = "task-runner-" + System.currentTimeMillis();
//
//                // Build the busybox pod specification
//                V1Pod pod = new V1PodBuilder()
//                        .withNewMetadata()
//                        .withName(podName)
//                        .endMetadata()
//                        .withNewSpec()
//                        .addNewContainer()
//                        .withName("busybox-container")
//                        .withImage("busybox")
//                        .withCommand("/bin/sh", "-c", task.getCommand())
//                        .endContainer()
//                        .withRestartPolicy("Never")
//                        .endSpec()
//                        .build();
//
//                // Create the Pod in the default namespace
//                api.createNamespacedPod("default", pod, null, null, null, null);
//
//                // Wait for the Pod to complete. A simple sleep is used here.
//                TimeUnit.SECONDS.sleep(10);
//
//                // Get the logs (output) from the Pod
//                String podOutput = api.readNamespacedPodLog(podName, "default", null, null, null, null, null, null, null, null, null);
//
//                execution.setEndTime(new Date());
//                execution.setOutput(podOutput);
//
//                // Clean up the Pod after execution
//                api.deleteNamespacedPod(podName, "default", null, null, null, null, null);
//
//            } catch (ApiException e) {
//                execution.setEndTime(new Date());
//                execution.setOutput("Execution failed: " + e.getResponseBody());
//            } catch (Exception e) {
//                execution.setEndTime(new Date());
//                execution.setOutput("Execution failed: " + e.getMessage());
//            }
//
//            task.getTaskExecutions().add(execution);
//            taskRepository.save(task);
//
//            return task;
//        });
//    }
//
//    // A simple validation to prevent malicious commands.
//    private boolean isUnsafeCommand(String command) {
//        // A simple check, a real-world application would need a more robust security mechanism
//        return command != null && (command.contains("rm -rf") || command.contains("sudo") || command.contains("&"));
//    }
//}
//-----------------------------------------------//
package com.chandan.task;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.util.ClientBuilder;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task putTask(Task task) {
        // Validation check for malicious code
        if (isUnsafeCommand(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected.");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    public Optional<Task> putTaskExecution(String taskId) {
        return taskRepository.findById(taskId).map(task -> {
            // Initialize the list outside the try block
            if (task.getTaskExecutions() == null) {
                task.setTaskExecutions(new ArrayList<>());
            }

            TaskExecution execution = new TaskExecution();
            execution.setStartTime(new Date());

            try {
                // Set up Kubernetes API client
                ApiClient client = ClientBuilder.defaultClient();
                Configuration.setDefaultApiClient(client);
                CoreV1Api api = new CoreV1Api();

                String podName = "task-runner-" + System.currentTimeMillis();

                // Manually build the V1Pod object without the V1PodBuilder
                V1ObjectMeta metadata = new V1ObjectMeta();
                metadata.setName(podName);

                V1Container container = new V1Container();
                container.setName("busybox-container");
                container.setImage("busybox");
                container.setCommand(Arrays.asList("/bin/sh", "-c", task.getCommand()));

                V1PodSpec podSpec = new V1PodSpec();
                podSpec.setContainers(Arrays.asList(container));
                podSpec.setRestartPolicy("Never");

                V1Pod pod = new V1Pod();
                pod.setApiVersion("v1");
                pod.setKind("Pod");
                pod.setMetadata(metadata);
                pod.setSpec(podSpec);

                // Create the Pod in the default namespace
                api.createNamespacedPod("default", pod, null, null, null, null);

                // Wait for the Pod to complete. A simple sleep is used here.
                TimeUnit.SECONDS.sleep(10);

                // Get the logs (output) from the Pod
                String podOutput = api.readNamespacedPodLog(podName, "default", null, null, null, null, null, null, null, null, null);

                execution.setEndTime(new Date());
                execution.setOutput(podOutput);

                // ............0
                // Clean up the Pod after execution
                //sapi.deleteNamespacedPod(podName, "default", null, null, null, null, null);

            } catch (ApiException e) {
                execution.setEndTime(new Date());
                execution.setOutput("Execution failed: " + e.getResponseBody());
            } catch (Exception e) {
                execution.setEndTime(new Date());
                execution.setOutput("Execution failed: " + e.getMessage());
            }

            task.getTaskExecutions().add(execution);
            taskRepository.save(task);

            return task;
        });
    }

    // A simple validation to prevent malicious commands.
    private boolean isUnsafeCommand(String command) {
        // A simple check, a real-world application would need a more robust security mechanism
        return command != null && (command.contains("rm -rf") || command.contains("sudo") || command.contains("&"));
    }
}