const AuthService = require("../services/AuthService");
const AuthUtils = require("../utils/AuthUtils");

exports.loginUser = async(req,res) => {
    const {email, password} = req.body;
    try {
        const user = await AuthService.authenticateUser(email, password);
        const token = await AuthService.generateToken(user);
        res.json({token});
    } catch (error) {
        res.status(401).send(error.message);
    }
};

exports.registerUser = async (req, res) => {
    const {email, password} = req.body;
    try {
        const salt = AuthUtils.generateSalt();
        const hashedPassword = AuthUtils.generatePassword(password, salt);
        const result = AuthService.registerUser({email, hashedPassword, salt});
        res.send(200).json({result});
    } catch (error) {
        res.send(404).json({message: "Something went wrong"});
    }
};

exports.changePassword = async(req, res) => {
    res.send("very good");
};

exports.logoutUser = async (req, res) => {
    try {
        const result = AuthService.logoutUser(req.body);
        res.status(200).json({message: "Logged out successfully"});
    } catch (error) {
        res.status(500).json({message: "Cannot process request. Retry after sometime."});
    }
};