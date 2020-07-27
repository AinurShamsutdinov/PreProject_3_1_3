

$('document').ready(function () {
    $('.table .btn').on('click', function(event){

        event.preventDefault();

        var href =$(this).attr('href');

        $.get(href, function (user, status) {
            $('#idEdit').val(user.id);
            $('#userNameEdit').val(user.userName);
            $('#lastNameEdit').val(user.lastName);
            $('#ageEdit').val(user.age);
            $('#emailEdit').val(user.email);
            $('#passwordEdit').val(user.password);
        });
        $('#editModal').modal();
    });
});