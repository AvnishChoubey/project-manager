const express = require("express");
const UserController = require("../controllers/UserController");

const router = express.Router();

router.get("/users", UserController.getAllUsers);
router.get("/users/:id", UserController.getUserById);
router.put("/users/:id", UserController.updateUserById);
router.delete("/users/:id", UserController.deleteUserById);

module.exports = router;