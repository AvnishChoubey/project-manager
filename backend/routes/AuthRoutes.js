const express = require("express");
const AuthController = require("../controllers/AuthController");

const router = express.Router();

router.post("/register", AuthController.registerUser);
router.get("/signin", AuthController.loginUser);
router.put("/change-password", AuthController.changePassword);
router.delete("/signout", AuthController.logoutUser);

module.exports = router;