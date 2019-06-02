package com.stomat.controllers.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/console")
public class ConsoleController {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public ConsoleController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @GetMapping
    public String getConsole(@RequestParam String sql, Model model) {
        model.addAttribute("sql", sql);
        return "admin/console";
    }

    @PostMapping
    public String postConsole(@RequestParam String sql, Model model) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

        model.addAttribute("result", result);
        model.addAttribute("sql", sql);
        return "admin/console";
    }
}
