package com.why.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.why.satoken.aspect.service.LogMethodCall;
import com.why.satoken.entity.po.Books;
import com.why.satoken.entity.base.Result;
import com.why.satoken.service.IBooksService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/books")
public class BooksController {
    @Resource
    IBooksService booksService;

    @LogMethodCall(securityLevel = "II")
    @PostMapping("/addBook")
    public Result<Boolean> addBook(@RequestBody Books book) {
//        StpUtil.checkPermission("insert_book");
        return Result.createSuccess(booksService.save(book));

    }

    @LogMethodCall(securityLevel = "III")
    @GetMapping("/deleteBook")
    public Result<Boolean> deleteBook(@RequestParam String id) {
//        StpUtil.checkPermission("delete_book");
        return Result.createSuccess(booksService.removeById(id));
    }

    @LogMethodCall(securityLevel = "II")
    @PostMapping("/updateBook")
    public Result<Boolean> updateBook(@RequestBody Books book) {
//        StpUtil.checkPermission("update_book");
        return Result.createSuccess(booksService.updateById(book));
    }

    @LogMethodCall(securityLevel = "I")
    @SaCheckLogin
    @GetMapping("/pageList")
    public Result<List<Books>> pageList() {
        return Result.createSuccess(booksService.list());
    }
}
