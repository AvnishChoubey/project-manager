const ProjectModel = require("../models/ProjectModel");
const UserModel = require("../models/UserModel");

// exports.createProject = async ({projectName, adminEmail, contributors}) => {
//     const project = new Project({
//         name: projectName,
//         tasks: [],
//         contributors,
//         admin: adminEmail
//     });
//     const result = await project.save();
//     return result;
// }

// exports.addContributor = async (name, email) => {
//     const user = UserModel.find({email: email});
//     const result = ProjectModel.updateOne({name: name},{$push: {contrinbutors: user}});
//     return result;
// }

// exports.getContributors = async (projectName) => {
//     const project = ProjectModel.findAll({name: projectName});
//     return project.contributors;
// }