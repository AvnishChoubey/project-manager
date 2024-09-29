const express = require("express");
const ProjectController = require("../controllers/ProjectController");

const router = express.Router();

router.get("/projects", ProjectController.getMembers);
router.get("/projects/:id", ProjectController.getProjectById);
router.post("/projects", ProjectController.addNewMember);
router.put("/projects/:id", ProjectController.updateProjectById);
router.delete("/projects/:id", ProjectController.deleteProjectById);

module.exports = router;