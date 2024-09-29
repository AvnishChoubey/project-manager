const express = require("express");
const CommentController = require("../controllers/CommentController");

const router = express.router();

router.get("/comments", CommentController.getAllComments)
router.get("/comments/:id", TaskController.getCommentById);
router.post("/comments", TaskController.createComment);
router.put("/comments/:id", TaskController.updateCommentById);
router.delete("/comments/:id", TaskController.deleteCommentById);

module.exports = router;