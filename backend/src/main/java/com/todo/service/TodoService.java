package com.todo.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.repositories.Todo;
import com.todo.repositories.TodoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private EntityManager entityManager;

    public Iterable<Todo> getTasks() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTaskById(Long task_id) {
        return todoRepository.findById(task_id);
    }

    public Optional<List<Todo>> getTasksByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<Todo> filterTasks(Map<String, Object> filterCriteria) {
        CriteriaBuilder cr = entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> cq = cr.createQuery(Todo.class);
        Root<Todo> root = cq.from(Todo.class);

       List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String, Object> entry : filterCriteria.entrySet()){
            String criterion = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                switch (criterion) {
                    case "user_id":
                        predicates.add(cr.equal(root.get("userId"), value));
                        break;
                    case "task_id":
                        predicates.add(cr.equal(root.get("taskId"), value));
                        break;
                    case "reminders":
                        predicates.add(cr.equal(root.get("reminders"), value));
                        break;
                    case "title":
                        predicates.add(cr.equal(root.get("title"), value));
                        break;
                    case "desc":
                        predicates.add(cr.equal(root.get("desc"), value));
                        break;
                    case "status":
                        predicates.add(cr.equal(root.get("status"), value));
                        break;
                }
            }
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
        
    }


}
