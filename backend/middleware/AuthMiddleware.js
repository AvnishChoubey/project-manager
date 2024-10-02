const jwt = require("jsonwebtoken");
const dbConfig = require("../config/dbConfig");
const AuthService = require("../services/AuthService");

exports.isAuthorised = (req, res, next) => {
    console.log(req);
    // const token = req.headers["authorization"]?.split(' ').[1];
    // if(!token)
    // return res.status(403).send("No token provided");
    // try {
    //     const decoded = await AuthService.verifyToken(token);
    //     req.userId = decoded._id;
    //     next();
    // } catch (error) {
    //     return res.status(401).send("Failed to authenticate token");
    // }
}