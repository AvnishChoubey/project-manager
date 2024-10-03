// const UserService = require("../services/UserService");
    
// exports.registerUser = async (req,res) => {
//     try {
//         console.log(req);
//         const { email, password} = req.body;
//         const {data} = await UserService.signup({email, password});
//         res.status(201).json(data);
//     } catch (error) {
//         res.status(500).json({message: error.message});
//     }
// };

// exports.loginUser = async (req,res) => {
//     const { email, password} = req.body;
//     const {data} = await UserService.login({email, password});
//     res.json(data);
// };

// exports.updatePassword =  async (req,res) => {
//     const {email, password} = req.body;
//     const {data} = await UserService.changePassword({email, password});
//     res.json(data);
// };

// exports.getUserTasks =  async(req,res) => {
//     const {email} = req.body;
//     const {data} = await UserService.getTasks({email});
//     res.json(data);
// };

// exports.getUserProjects = async(req,res) => {
//     const data = await UserService.getProjects(req.body);
//     res.json(data);
// }