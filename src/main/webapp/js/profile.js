// Get modal element
var modal = document.getElementById("taskModal");
// Get open modal button
var btn = document.getElementById("addTaskBtn");
// Get close button
var span = document.getElementsByClassName("close")[0];
// Get all edit buttons
var editButtons = document.querySelectorAll(".editBtn");
// Get all delete buttons
var deleteButtons = document.querySelectorAll(".deleteBtn");

// Listen for open click
btn.onclick = function() {
    // Clear the form fields
    document.getElementById("taskTitle").value = "";
    document.getElementById("taskTitle").removeAttribute("readonly");
    document.getElementById("taskDate").value = "";
    document.getElementById("taskStatus").value = "";
    modal.style.display = "block";
};

// Listen for edit button clicks
editButtons.forEach(function(button) {
    button.addEventListener("click", function() {
        // Populate the form fields with the task data
        document.getElementById("taskTitle").value = this.getAttribute("data-title");
        document.getElementById("taskTitle").setAttribute("readonly", true);
        document.getElementById("taskDate").value = this.getAttribute("data-date");
        document.getElementById("taskStatus").value = this.getAttribute("data-status");
        modal.style.display = "block";
    });
});

// Listen for delete button clicks
deleteButtons.forEach(function(button) {
    button.addEventListener("click", function() {
        var taskTitle = this.getAttribute("data-title");
        if (confirm("Are you sure you want to delete this task?")) {
            fetch(`/todo-app/delete?title=${encodeURIComponent(taskTitle)}`, {
                method: "DELETE"
            });
            location.reload();
        }
    });
});

// Listen for close click
span.onclick = function() {
    modal.style.display = "none";
};

// Listen for outside click
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};