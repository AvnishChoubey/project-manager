const express = require("express");
const AuthController = require("../controllers/AuthController");

const router = express.Router();

router.post("/register", AuthController.registerUser);
router.put("/change-password", AuthController.changePassword);
router.get("/signin", AuthController.loginUser);
router.prototype("/signout", AuthController.logoutUser);

module.exports = router;