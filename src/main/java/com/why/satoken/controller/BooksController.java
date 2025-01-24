package com.why.satoken.controller;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import com.why.satoken.entity.Books;
import com.why.satoken.entity.base.Result;
import com.why.satoken.service.IBooksService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muyu
 * @since 2025-01-23
 */
@RestController
@RequestMapping("/auth/books")
public class BooksController {
    @Resource
    IBooksService booksService;

    @PostMapping("/addBook")
    public Result<Boolean> addBook(@RequestBody Books book) {
        StpUtil.checkPermission("insert_book");
        return Result.createSuccess(booksService.save(book));

    }
    @GetMapping("/deleteBook")
    public Result<Boolean> deleteBook(@RequestParam String id) {
        StpUtil.checkPermission("delete_book");
        return Result.createSuccess(booksService.removeById(id));
    }
    @PostMapping("/updateBook")
    public Result<Boolean> updateBook(@RequestBody Books book) {
        StpUtil.checkPermission("update_book");
        return Result.createSuccess(booksService.updateById(book));
    }
    @GetMapping("/pageList")
    public Result<List<Books>> pageList() {
        StpUtil.checkPermission("select_book");
        return Result.createSuccess(booksService.list());
    }
}
