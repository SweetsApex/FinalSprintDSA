package com.keyin.binary_search_tree.Controller;

import com.keyin.binary_search_tree.Entity.*;
import com.keyin.binary_search_tree.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TreeController {

    @Autowired
    private TreeService treeService;

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enter-numbers";
    }

    @PostMapping("/process-numbers")
    public String processNumbers(@RequestParam String numbers, Model model) {
        List<Integer> values = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Tree tree = treeService.createTree(values);
        String treeJson = treeService.convertTreeToJson(tree);

        model.addAttribute("tree", tree);
        model.addAttribute("treeJson", treeJson);

        return "results-page";  // Return the name of the Thymeleaf template
    }

    @GetMapping("/previous-trees")
    public String showPreviousTrees(Model model) {
        List<Tree> trees = treeService.getAllTrees();
        List<String> treeJsonList = trees.stream()
                .map(treeService::convertTreeToJson)
                .collect(Collectors.toList());

        model.addAttribute("trees", trees);
        model.addAttribute("treeJsonList", treeJsonList);
        return "previous-trees";
    }
}
