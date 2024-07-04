package com.dedee.route

import com.dedee.model.admin.AdministratorRequest
import com.dedee.repository.admin.AdminRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.currentPassword
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.updatedPassword
import util.DatabaseEntityFields.AdminUsername
import util.DatabaseEntityFields.Email
import util.Routes.changeAdminPassword
import util.Routes.deleteAdmin
import util.Routes.getAdminByAdminUsername
import util.Routes.getAdminByEmail
import util.Routes.getAllAdmins
import util.Routes.registerAdmin
import util.Routes.updateAdmin

fun Application.adminRoutes(adminRepository: AdminRepository) {
    routing {
        get(getAllAdmins) {
            val result = adminRepository.getAllAdmins()
            call.respond(result)
        }
        get(getAdminByEmail) {
            val email = call.parameters[Email] ?: emptyString
            val result = adminRepository.getAdminByEmail(email)
            call.respond(result)
        }
        get(getAdminByAdminUsername) {
            val adminUsername = call.parameters[AdminUsername] ?: emptyString
            val result = adminRepository.getAdminByAdminUserName(adminUsername)
            call.respond(result)
        }

        post (registerAdmin){
            val adminRequest = call.receive<AdministratorRequest>()
            val result = adminRepository.registerAdmin(adminRequest)
            call.respond(HttpStatusCode.OK, result)
        }

        put(updateAdmin){
            val parameter = call.parameters[parameter] ?: emptyString
            val adminRequest = call.receive<AdministratorRequest>()
            val result = adminRepository.updateAdmin(parameter, adminRequest)
            call.respond(HttpStatusCode.OK, result)
        }

        put(changeAdminPassword){
            val parameter = call.parameters[parameter] ?: emptyString
            val currentPassword = call.parameters[currentPassword] ?: emptyString
            val updatedPassword = call.parameters[updatedPassword] ?: emptyString
            val result = adminRepository.changePassword(parameter, currentPassword, updatedPassword)
            call.respond(result)
        }

        delete (deleteAdmin){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = adminRepository.deleteAdmin(parameter)
            call.respond(result)
        }
    }
}
