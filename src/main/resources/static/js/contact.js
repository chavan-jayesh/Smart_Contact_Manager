console.log("This is contact JS!");

const viewContactModal = document.getElementById("view_contact_modal");
const baseURL = "localhost:8080";

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options.instanceOptions);

function openContactModal(){
    contactModal.show();
}

function closeContactModal(){
    contactModal.hide();
}

//function call to load contact data

async function loadContactData(id){
    console.log(id);

    try{
        const data = await (
            await fetch(`http://${baseURL}/api/contacts/${id}`)
        ).json();
        console.log(data);

        document.getElementById("contact_name").innerHTML = data.name;
        document.getElementById("contact_email").innerHTML = data.email;
        openContactModal();
    }
    catch(error){
        console.log("Error: "+ error);
    }
}

//Delete Contact
 
async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    customClass: {
    confirmButton: 'bg-red-600 hover:bg-red-800 text-white px-4 py-2 rounded',
    cancelButton: 'bg-gray-400 hover:bg-gray-600 text-black px-4 py-2 rounded',
  }
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      const url = `http://${baseURL}/user/contacts/delete/` + id;
      window.location.replace(url);
    }
  });
}




