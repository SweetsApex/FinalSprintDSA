package com.keyin.binary_search_tree.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.binary_search_tree.Entity.*;
import com.keyin.binary_search_tree.Repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.*;

@Service
public class TreeService {

    @Autowired
    private TreeRepository treeRepository;

    public Node insert(Node root, int value) {
        if (root == null) {
            root = new Node();
            root.setValue(value);
            return root;
        }
        if (value < root.getValue()) {
            root.setLeft(insert(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insert(root.getRight(), value));
        }
        return root;
    }

    public Tree createTree(List<Integer> values) {
        Node root = null;
        for (int value : values) {
            root = insert(root, value);
        }
        Tree tree = new Tree();
        tree.setInputNumbers(values.toString());
        tree.setRootNode(root);
        return treeRepository.save(tree);
    }

    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    public String convertTreeToJson(Tree tree) {
        ObjectMapper mapper = new ObjectMapper();
        return convertNodeToJson(tree.getRootNode(), mapper).toPrettyString();  // Pretty print for readability
    }

    private ObjectNode convertNodeToJson(Node node, ObjectMapper mapper) {
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("value", node.getValue());
        if (node.getLeft() != null) {
            jsonNode.set("left", convertNodeToJson(node.getLeft(), mapper));
        } else {
            jsonNode.put("left", (String) null);
        }
        if (node.getRight() != null) {
            jsonNode.set("right", convertNodeToJson(node.getRight(), mapper));
        } else {
            jsonNode.put("right", (String) null);
        }
        return jsonNode;
    }
}
