const express = require("express");
const TaskController = require("../controllers/TaskController");

const router = express.router();
router.get("/tasks", TaskController.getAllTasks);
router.get("/tasks/:id", TaskController.getTaskById);
router.post("/tasks", TaskController.createTask);
router.put("/tasks/:id", TaskController.updateTaskById);
router.delete("/tasks:id", TaskController.deleteTaskById);

module.exports = router;