package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> repoUsers = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserUtils.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repoUsers.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repoUsers.put(user.getId(), user);
            return user;
        }
        return repoUsers.computeIfPresent(user.getId(), (integer, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repoUsers.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return (List<User>) repoUsers.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repoUsers.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst().orElse(null);
    }
}
