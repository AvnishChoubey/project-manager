const ProjectService = require("../services/ProjectService");

exports.createNewProject = async (req,res) => {
    const {data} = ProjectService.createNewProject(req.body);
    res.json(data);
}

// exports.getMembers = async (req,res) => {
//     const {data} = ProjectService.getMembers(req.body);
//     res.json(data);
// };

// exports.addNewMember = async (req,res) => {
//     const {data} = ProjectService.addMember(req.body);
//     res.json(data);
// };

// exports.addNewTask = async (req,res) => {
//     const {data} = ProjectService.newTask(req.body);
//     res.json(data);
// };

// exports.updateTask = async (req,res) => {
//     const {data} = ProjectService.updateTask(req.body);
//     res.json(data);
// };

// exports.updateStageOfTask = async (req,res) => {
//     const {data} = ProjectService.updateStage(req.body);
//     res.json(data);
// };