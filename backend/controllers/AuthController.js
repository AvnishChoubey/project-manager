const User = require("../models/UserModel");
const jwt = require("jsonwebtoken");

exports.registerUser = async (req, res) => {
    try {
        const {email, password, firstName, lastName, role} = req.body;
        password = await bcrypt.hash(password, 10);
        const newUser = await User.create({email, password, firstName, lastName, role});
        res.status(201).json({message: "User created", ...newUser});
    } catch (error) {
        console.log("Error creating user: ", error);
        res.status(400).json(error.message);
    }
};

exports.loginUser = async (req,res) => {
    try {
        const {email, password} = req.body;
        const existingUser = await User.findOne({email});
        if(!existingUser) {
            res.status(400).json({message: "user doesn't exist"});
        } else {
            const match = bcrypt.compare(password, existingUser.password);
            if(!match){
                res.status(401).json({message: "wrong password"});
            } else {
                jwt.sign(existingUser, SECRET_KEY, (error, token) => {
                    if(error) {
                        console.log(error);
                    } else {
                        console.log(token);
                        res.status(201).json({token});
                    }
                });
            }
        }
    } catch (error) {
        res.status(500).send(error.message);
    }
};

exports.changePassword = async(req, res) => {
    try {
        const {email, oldPassword, newPassword} = req.body;
        const existingUser = await User.findOne({email});
        if(!existingUser) {
            res.status(400).json({message: "user doesn't exists"});
        } else {
            const match = bcrypt.compare(oldPassword, existingUser.password);
            if(!match){
                res.status(401).json({message: "wrong password"});
            } else {
                newPassword = bcrypt.hash(newPassword,10);
                const result = await User.updateOne(newPassword);
                res.status(201).json({message: "password updated successfully"});
            }
        }
    } catch (error) {
        res.status(500).send(error.message);
    }
};

exports.logoutUser = async (req, res) => {
    try {
        res.status(200).json({message: "Logged out successfully"});
    } catch (error) {
        res.status(500).json({message: "Cannot process request. Retry after sometime."});
    }
};