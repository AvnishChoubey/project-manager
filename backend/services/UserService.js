const UserRepository = require("../repositories/UserRepository");
// import {generateSalt, generatePassword, validatePassword, generateSignature} from "../utils/AuthUtils.js";
require("dotenv").config();

// exports.signin = async (userInputs) => {
//     const {email, password} = userInputs;
//     const existingUser = await UserRepository.findUserByEmail(email);
//     if(existingUser)
//     {
//         const validPass = await validatePassword(password, existingUser.password, existingUser.salt);
//         if(validPass)
//         {
//             const token = await generateSignature({email: existingUser.email, _id: existingUser._id});
//             return formateData({_id: existingUser._id, token});
//         }
//     }
//     return formateData(null);
// }

// exports.signup = async (userInputs) => {
//     const {email, password} = userInputs;
//     const existingUser = await UserRepository.findUserByEmail(email);
//     if(!existingUser)
//     {
//         const salt = await generateSalt();
//         const userPassword = generatePassword(password,salt);
//         this.repository.createUser({email, password: userPassword, salt});
//     }
// }

// exports.changePassword = async (userInputs) => {
//     const {email, password} = userInputs;
//     const existingUser = await UserRepository.findUserByEmail(email);
//     if(existingUser)
//     {
//         const userPassword = generatePassword(password, existingUser.salt);
//         const result = UserRepository.updatePassword({email: existingUser.email, password: userPassword, salt: existingUser.salt});
//         return result;
//     }
//     return {message: "User Not Found"};
// }

// exports.getTasks = async (userEmail) => {
//     const result = UserRepository.getTasksByEmail({email: userEmail});
//     return result;
// }

// exports.getProjects = async (userEmail) => {
//     try {
//         const result = UserRepository.getProjectsByEmail({email: userEmail});
//         return result;
//     } catch (error) {
//         console.error("Error: ", error);
//     }
// }