function clearErrors() {
    for (var loopCounter = 0;
        loopCounter < document.forms["contactForm"].elements.length;
        loopCounter++) {
        if (document.forms["contactForm"].elements[loopCounter]
           .parentElement.className.indexOf("has-") != -1) {

            document.forms["contactForm"].elements[loopCounter]
               .parentElement.className = "form-group";
        }
    }
}
function resetForm() {
    clearErrors();
    document.forms["contactForm"]["name"].value = "";
    document.forms["contactForm"]["email"].value = "";
    document.forms["contactForm"]["phone"].value = "";
    document.forms["contactForm"]["textarea"].value = "";

    document.forms["contactForm"]["name"].focus();
}

submitButton.addEventListener("click",() => {
  clearErrors();
  var name = document.forms["contactForm"]["name"].value;
  var email = document.forms["contactForm"]["email"].value;
  var phone = document.forms["contactForm"]["phone"].value;

    if(name == " "|| !isNan(name) ){
      alert("Name is required and must not be a number");
      document.forms["contactForm"]["name"].parentElement.className = "form-group has-error";
      document.forms["contactForm"]["name"].focus();
      return false;

    }
    if (email == "" || !isNaN(email)) {
    alert("email must be filled in with an email address");
    document.forms["contactForm"]["email"]
       .parentElement.className = "form-group has-error"
    document.forms["contactForm"]["email"].focus();
    return false;
}
})

document.getElementById("results").innerText = " is your name and your email is " + name;;
