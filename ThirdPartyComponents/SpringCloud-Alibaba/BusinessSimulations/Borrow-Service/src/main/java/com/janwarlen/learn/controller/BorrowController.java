package com.janwarlen.learn.controller;

import com.janwarlen.learn.dataobject.BorrowRecordDO;
import com.janwarlen.learn.entity.Book;
import com.janwarlen.learn.entity.User;
import com.janwarlen.learn.feign.LibraryService;
import com.janwarlen.learn.feign.UserService;
import com.janwarlen.learn.service.BorrowService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class BorrowController {

    private HashMap<Integer, Set<Integer>> cache = new HashMap<>();

    @Resource
    UserService userService;
    @Resource
    LibraryService libraryService;
    @Autowired
    BorrowService borrowService;

    @RequestMapping("/borrow/{uid}/{bid}")
    @GlobalTransactional
    public String borrow(@PathVariable("uid") int uid, @PathVariable("bid") int bid) {
        User user = userService.getUser(uid);
        if (Objects.isNull(user)) {
            return "用户不存在";
        }
        BorrowRecordDO borrowRecord = borrowService.checkBookIsBorrowedByUid(uid, bid);
        if (Objects.nonNull(borrowRecord)) {
            return "该书已借阅，请勿重复借阅";
        }
        Book book = libraryService.getBookById(bid);
        if (book.getInventory() == 0) {
            return "该书已借完";
        }
        // 借书记录
        borrowService.borrowBookByUid(uid, bid);
        // 图书库存
        book.setInventory(book.getInventory() - 1);
        libraryService.updateBookById(book);
        return "success";
    }

    @RequestMapping("/borrow/getMember")
    public List<User> getMember() {
        Set<Integer> integers = cache.keySet();
        if (integers.isEmpty()) {
            return Collections.emptyList();
        }
        return userService.getUserByIds(integers);
    }
}
