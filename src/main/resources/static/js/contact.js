console.log("This is contact JS!");

const viewContactModal = document.getElementById("view_contact_modal");
const baseURL = "https://smartcontacts.online";

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
        document.getElementById("contact_phone").innerHTML = data.phoneNumber;
        document.getElementById("contact_address").innerHTML = data.address;
        document.getElementById("contact_image").src = data.picture;
        document.getElementById("contact_about").innerHTML = data.description;
        document.getElementById("contact_website").innerHTML = data.websiteLink;
        document.getElementById("contact_linkedIn").innerHTML = data.linkedinLink;
        document.getElementById("contact_website").href = data.websiteLink;
        document.getElementById("contact_linkedIn").href = data.linkedinLink;
        document.getElementById("edit_contact").href = `/user/contacts/view/${data.id}`;

        const contactFavorite = document.getElementById("contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML = "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
        } else {
            contactFavorite.innerHTML = "Not Favorite Contact";
        }
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
      const url = `${baseURL}/user/contacts/delete/` + id;
      window.location.replace(url);
    }
  });
}

// Exporting Contact Data into excel sheet
function exportData(){
    TableToExcel.convert(document.getElementById("contact_table_info"), {
        name: "contacts.xlsx",
        sheet: {
            name: "Sheet 1"
        }
    });
};