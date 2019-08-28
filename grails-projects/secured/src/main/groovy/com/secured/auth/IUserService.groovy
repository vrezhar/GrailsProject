package com.secured.auth

interface IUserService
{
    def generateCoordinates()
    def exists(String username)
    def create(String username,String password,String firstName, String lastName)
    def assignRole(User usr,Role r)
    def hasRole(User usr, Role r)
    def hasRole(User usr,String role)
    int passwordComplexity(String password)
    def assignRole(User usr,String role)
    def addToCoordinates(User usr,Map coordinates)
}