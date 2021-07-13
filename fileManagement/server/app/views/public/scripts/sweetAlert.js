const swal = require('sweetalert2');

function enterDirectoryName(elemPath){
    var form = document.createElement("div");
      form.innerHTML = `
        <form action="/createNewDir" method="GET" enctype="multipart/form-data">
        <input class="form-control mr-sm-2" type="text" placeholder="New Directory" aria-label="Search">
        <button type="submit" class="btn btn-outline-primary">
           CREATE
        </button> 
        <input type="hidden" name="newfilePath" value="`+elemPath + `">
        <input type="hidden" name="filePath" value="<%= files.path %>"></li>
    </form>
      `;

      swal.fire({
        title: 'Create a new Directory',
        text: 'Enter directory name',
        content: form,
        buttons: {
          cancel: "Cancel",
          catch: {
            text: "Create",
            value: 5,
          },
        }
      }).then((value) => {
        //console.log(slider.value);
      });
}