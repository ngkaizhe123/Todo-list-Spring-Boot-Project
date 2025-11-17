package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // 显示所有待办事项
    @GetMapping
    public String listTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        // 添加调试信息
        System.out.println("=== 调试待办事项数据 ===");
        for (Todo todo : todos) {
            System.out.println("任务: " + todo.getTitle());
            System.out.println("创建时间: " + todo.getCreatedAt());
            System.out.println("创建时间是否为null: " + (todo.getCreatedAt() == null));
            if (todo.getCreatedAt() != null) {
                System.out.println("格式化时间: " + todo.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        }
        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", new Todo()); // 用于表单绑定
        return "todo-list";
    }

    // 添加新的待办事项
    @PostMapping
    public String addTodo(@ModelAttribute Todo newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.saveTodo(newTodo);
        }
        return "redirect:/todos"; // 重定向到列表页
    }

    // 切换完成状态
    @PostMapping("/{id}/toggle")
    public String toggleComplete(@PathVariable Long id) {
        todoService.toggleComplete(id);
        return "redirect:/todos";
    }

    // 删除待办事项
    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }
}
