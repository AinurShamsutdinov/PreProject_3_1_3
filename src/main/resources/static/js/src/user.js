// when the webpage is loaded
$(document).ready(function(e) {
    var currentUser = $.ajax({
        async: false,
        type : "GET",
        url : "currentuser",
        dataType: "json",
        success : function (response){
            console.info("Success current user");
        },
        error : function(e) {
            console.log("ERROR current user: ", e);
        }
    }).responseJSON;


    fillTable();

    function fillTable (){
        $('#currentUserTable').empty();
        var userRoles = "";
        if (currentUser.roles.length === 1){
            userRoles += "USER";
        }
        if (currentUser.roles.length === 2){
            userRoles += "USER ADMIN";
        }

        $("#idTable").append(currentUser.id);
        $("#nameTable").append(currentUser.username);
        $("#lastNameTable").append(currentUser.lastName);
        $("#ageTable").append(currentUser.age);
        $("#emailTable").append(currentUser.email);
        $("#roleTable").append(userRoles);

        $("#spanUserEmail").text(currentUser.email);
        $("#spanUserRoles").text(userRoles);
    }
});