package cn.learncoding.springmvc.controller;

import cn.learncoding.springmvc.vo.Msg;
import cn.learncoding.springmvc.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author learncoding.cn
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private static AtomicLong id = new AtomicLong(1);
	private static Map<Long, User> db = new ConcurrentHashMap<Long, User>();

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		if (!db.containsKey(id)) {
			return Msg.notFind();
		}
		return Msg.ok(db.get(id));
	}

	@GetMapping
	public ResponseEntity<?> list() {
		return Msg.ok(db.values());
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody User user) {
		Long userId = id.getAndIncrement();
		user.setId(userId);
		db.put(userId, user);
		return Msg.created();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateSome(@PathVariable("id") Long id, @RequestBody User user) {
		if (!db.containsKey(id)) {
			return Msg.notFind();
		}
		User oldUser = db.get(id);
		if (!StringUtils.isEmpty(user.getName())){
			oldUser.setName(user.getName());
		}
		if (user.getAge() != null){
			oldUser.setAge(user.getAge());
		}
		return Msg.noContent();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user) {
		if (!db.containsKey(id)) {
			return Msg.notFind();
		}
		db.put(id, user);
		return Msg.noContent();
	}

	@PutMapping
	public ResponseEntity<?> batchUpdate(@RequestBody List<User> users) {
		for (User user : users) {
			db.put(user.getId(), user);
		}
		return Msg.noContent();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (!db.containsKey(id)) {
			return Msg.notFind();
		}
		db.remove(id);
		return Msg.noContent();
	}
}
