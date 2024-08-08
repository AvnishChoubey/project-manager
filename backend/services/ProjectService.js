const ProjectRepository = require("../repositories/ProjectRepository.js");

// exports.newProject = async (projectInputs) => {
//     const {adminEmail, name, contributers, tasks} = projectInputs;
//     const result = ProjectRepository.createProject({adminEmail, name, contributers});
// }

// exports.getMembers = async (projectInputs) => {
//     const projectName = projectInputs;
//     const existingProject = await ProjectRepository.getContributors(toUpperCase(projectName));
//     if(existingProject)
//     {
//         const members = existingProject.contributers;
//         return members;
//     }
// }

// exports.addMember = async (projectInputs) => {
//     const {projectName, userEmail} = projectInputs;
//     const result = await ProjectRepository.addContributor(projectName, userEmail);
// }

// exports.newTask = async (projectInputs) => {
//     const {projectId, tasks} = projectInputs;
//     const result = ProjectRepository.addTask({projectId, tasks});
// }