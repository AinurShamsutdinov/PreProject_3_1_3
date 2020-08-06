// when the webpage is loaded

$(document).ready(function(e) {
    var userDelete;
    var userEdit;
    var userList = $.ajax({
        async: false,
        type : "GET",
        url : "userlist",
        dataType: "json",
        success : function (response){
            console.info("Success 1");
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    }).responseJSON;

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
        $('#usertable').empty();
        for (var i in userList) {
            var userRoles = "";
            if (userList[i].roles.length === 1){
                userRoles += "USER";
            }
            if (userList[i].roles.length === 2){
                userRoles += "USER ADMIN";
            }
            $("#usertable").
            append("<tr> \
                            <td>" +  userList[i].id + "</td> \
                            <td>" +  userList[i].userName + "</td> \
                            <td>" +  userList[i].lastName + "</td> \
                            <td>" +  userList[i].age + "</td> \
                            <td>" +  userList[i].email + "</td> \
                            <td>" +  userRoles + "</td> \
                            <td> \ <button id='edit' class='btn btn-info' data-toggle='modal' data-target='#editModal'>Edit</button> \ </td> \
                           <td> \<button id='delete' class='btn btn-danger' data-toggle='modal' data-target='#deleteModal'>Delete</button> \ </td> \
                        </tr>");
        }

        $('#currentUserTable').empty();
        var userRoles = "";
        if (currentUser.roles.length === 1){
            userRoles += "USER";
        }
        if (currentUser.roles.length === 2){
            userRoles += "USER ADMIN";
        }
        $("#currentUserTable").append("<tr> \
                            <td>" +  currentUser.id + "</td> \
                            <td>" +  currentUser.userName + "</td> \
                            <td>" +  currentUser.lastName + "</td> \
                            <td>" +  currentUser.age + "</td> \
                            <td>" +  currentUser.email + "</td> \
                            <td>" +  userRoles + "</td> \
                        </tr>");


        $("#spanUserEmail").empty();
        $("#spanUserRoles").empty();

        $("#spanUserEmail").text(currentUser.email);
        $("#spanUserRoles").text(userRoles);
    }

    function updateTable(){
        userList =$.ajax({
            async: false,
            type : "GET",
            url : "userlist",
            dataType: "json",
            success : function (response){
                console.info("Success 1");
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        }).responseJSON;
    }


    $('table').on('click', 'button[id="edit"]', function(e) {
        var userId = $(this).closest('tr').children('td:first').text();
        var arrayIndex;
        var userBoolean = false;
        var adminBoolean = false;

        for ( i = 0; i < userList.length; i++){
            if (userList[i].id == userId){
                arrayIndex = i;
                break;
            }
        }
        userEdit = userList[arrayIndex]
        console.log(userId);
        console.log(arrayIndex)

        $("#idEdit").val(userEdit.id);
        $("#userNameEdit").val(userEdit.username);
        $("#lastNameEdit").val(userEdit.lastName);
        $("#ageEdit").val(userEdit.age);
        $("#emailEdit").val(userEdit.email);
        $("#passwordEdit").val(userEdit.password);

        for (var i = 0; i < userEdit.roles.length; i++){
            if (userEdit.roles[i].roleName === 'ROLE_USER'){
                userBoolean = true;
            } else if (userEdit.roles[i].roleName === 'ROLE_ADMIN'){
                adminBoolean = true;
            }
        }
        console.log("userboolean" + userBoolean + "adminBoolean" + adminBoolean);

        $('#editSelect').empty();
        if (userBoolean){
            $('#editSelect').append('<option value="0" selected>USER</option>');
        } else {
            $('#editSelect').append('<option value="0">USER</option>');
        }

        if (adminBoolean){
            $('#editSelect').append('<option value="1" selected>ADMIN</option>');
        } else {
            $('#editSelect').append('<option value="1">ADMIN</option>')
        }
    });

    $("#editbutton").click(function () {
        var idNum = parseInt($("#idEdit").val());
        var rolesSelected = [];
        $('#editSelect :selected').each(function () {
            rolesSelected[$(this).val()] = $(this).text();
        });

        userEdit.userName = $("#userNameEdit").val();
        userEdit.lastName = $("#lastNameEdit").val();
        userEdit.age = parseInt($("#ageEdit").val());
        userEdit.email = $("#emailEdit").val();
        userEdit.password = $("#passwordEdit").val();
        userEdit.roles = rolesSelected

        $.ajax({
            url: "edituser",
            type: "POST",
            contentType:"application/json",
            data: JSON.stringify(userEdit),
            dataType: "json",
            complete: function (data) {
                console.log("Success edit");
                updateTable();
                fillTable();
            }
        });
        $('#usertable').empty();
    });

    $('table').on('click', 'button[id="delete"]', function(e) {
        var userId = $(this).closest('tr').children('td:first').text();
        var arrayIndex;
        var userBoolean = false;
        var adminBoolean = false;
        var roles;

        for ( i = 0; i < userList.length; i++){
            if (userList[i].id == userId){
                arrayIndex = i;
                break;
            }
        }
        userDelete = userList[arrayIndex]
        console.log(userId);
        console.log(arrayIndex);
        console.log("delete user " + userDelete.username);

        $("#idDelete").val(userDelete.id);
        $("#userNameDelete").val(userDelete.username);
        $("#lastNameDelete").val(userDelete.lastName);
        $("#ageDelete").val(userDelete.age);
        $("#emailDelete").val(userDelete.email);
        $("#passwordDelete").val(userDelete.password);

        for (var i = 0; i < userDelete.roles.length; i++){
            if (userDelete.roleName === 'ROLE_USER'){
                roles[i] = 'USER'
                userBoolean = true;
            } else if (userDelete.roleName === 'ROLE_ADMIN'){
                roles[i] = 'ADMIN'
                adminBoolean = true;
            }
        }
        userDelete.roles = roles;

        $('#deleteSelect').empty();
        if (userBoolean){
            $('#deleteSelect').append('<option value="user" selected>USER</option>');
        } else {
            $('#deleteSelect').append('<option value="user">USER</option>');
        }

        if (adminBoolean){
            $('#deleteSelect').append('<option value="admin" selected>ADMIN</option>');
        } else {
            $('#deleteSelect').append('<option value="admin">ADMIN</option>')
        }
    })

    $("#deletebutton").click(function () {
        $.ajax({
            url: "deleteuser/" + userDelete.id,
            type: "DELETE",
            complete: function (data) {
                console.log("Complete delete" + data);
            },
            error: function (err) {
                console.log("Error delete" + err);
            }
        });
        updateTable();
        fillTable();
    });

    // addUser
    $("#addbutton").click(function () {
        var userAdd = new Object();

        var rolesSelected = [];
        $('#addSelect :selected').each(function () {
            rolesSelected[$(this).val()] = $(this).text();
        });

        userAdd.userName = $("#userNameAdd").val();
        userAdd.lastName = $("#lastNameAdd").val();
        userAdd.age = parseInt($("#ageAdd").val());
        userAdd.email = $("#emailAdd").val();
        userAdd.password = $("#passwordAdd").val();
        userAdd.roles = rolesSelected;

        var token = $('#csrfToken').val();
        var header = $('#csrfHeader').val();
        $.ajax({
            type: "POST",
            cache: false,
            url: "adduser",
            data: JSON.stringify(userAdd),
            //async: true,
            dataType: "json",
            contentType:"application/json",
            complete: function (data) {
                console.log("Add success " + data);
                updateTable();
                fillTable();
                $("#userNameAdd").val("");
                $("#lastNameAdd").val("");
                $("#ageAdd").val("");
                $("#emailAdd").val("");
                $("#passwordAdd").val("");
            },
            error: function (err) {
                console.log("Add error" + err);
            }
        });
    });
});