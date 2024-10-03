const express = require("express");
const cors = require("cors");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const User = require("./models/UserModel");
const Project = require("./models/ProjectModel");
const Task = require("./models/TaskModel");
const Comment = require("./models/CommentModel");

const app = express();
app.use(express.json());
app.use(cors());

const PORT = process.env.PORT || 3000;

app.post("/register", async (req, res) => {
    try {
        const {email, password, firstName, lastName, role} = req.body;
        password = await bcrypt.hash(password, 10);
        const newUser = await User.create({email, password, firstName, lastName, role});
        res.status(201).json({message: "User created", ...newUser});
    } catch(error) {
        console.log("Error creating user: ", error);
        res.status(400).json(error.message);
    }
});

app.get("/signin", async (req, res) => {
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
    } catch(error) {
        res.status(500).send(error.message);
    }
});

app.put("/change-password", async (req, res) => {
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
    } catch(error) {
        res.status(500).send(error.message);
    }
});

app.delete("/signout", AuthController.logoutUser);

app.get("/projects", async (req, res) => {
    try {
        const projects = await Project.find({});
        res.status(200).json(projects);
    } catch(error) {
        res.status(500).send(error.message);
    }
});

app.get("/projects/:projectId", async (req, res) => {
    try {
        const project = await Project.findById(req.params.projectId);
        if(project) {
            res.status(200).json(project);
        } else {
            res.status(404).json({message: "project not found"});
        }
    } catch(error) {
        res.status(500).send(error.message);
    }
});

app.put("/projects/:projectId", async (req, res) => {
    try {
        const project = await Project.findByIdAndUpdate(req.params.projectId, req.body, {new: true});
        if(project) {
            console.log(project);
            res.status(200).json({message: "Updated"});
        } else {
            res.status(404).json({message: "Not found"});
        }
    } catch(error) {
        res.status(500).send(error.message);
    }
});

app.delete("/projects/:projectId", async (req, res) => {
    try {
        await Project.findByIdAndDelete(req.params.projectId);
        res.status(200).json({message: "project deleted successfully"});
    } catch {
        res.status(500).send(error.message);
    }
});

app.get("/projets/:projectId/tasks", async (req, res) => {
    try {
        const tasks = Task.find({project: req.params.projectId});
        res.status(200).json(tasks);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

app.get("/projects/:projectId/tasks/:taskId", async(req,res) => {
    try {
        const task = await Task.findById(req.params.taskId);
        if(!task) {
            res.status(404).json({message: "task not found"});
        } else {
            res.status(200).json(task);
        }
    } catch (error) {
        res.status(500).send(error.message);
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});