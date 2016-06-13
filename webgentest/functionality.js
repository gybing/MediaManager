function toggleDataDiv(divID) {
	var div = document.getElementById(divID);
		
	if(div.style.height == "" || div.style.height == "0px") {
		div.style.border = "solid 2px white";
		div.style.height = "150px";
	}
	else {
		div.style.height = "0px";
		div.style.border = "none";
	}		
}

function printDivs() {
	var outputHTML = "";
	var textField = document.getElementById("search-text-field");
	
	var searchText = textField.value;
	for(i in movies) {
		var adding = true;
		if(searchText == "") {
			adding = true;
		} else {
			adding = movies[i].toLowerCase().indexOf(searchText.toLowerCase()) > -1;
		}
		
		if(adding) {
			outputHTML += "<div class = 'movie-div'>";
			
			outputHTML += "<div class = 'title-div' onclick = 'toggleDataDiv(\"data-div-" + i + "\");'>";
			outputHTML += "<h3>" + movies[i] + "</h3>";
			outputHTML += "</div>";

			outputHTML += "<div class = 'data-div' style = 'height:0px' id = 'data-div-" + i + "'>";
			
			outputHTML += "<img class = 'data-div-image' src = './img/media/movies/" + images[i] + "' onclick = 'openImage(this);'></img>";
			outputHTML += "<div class = 'p-div'>";
			outputHTML+= "<p class = 'test-p'>hi</p>";
			outputHTML += "</div>";
			
			
			//End of data div
			outputHTML += "</div>";
			
			//End of movie div
			outputHTML += "</div>";	
		}
	}
	
	document.getElementById("content-div").innerHTML = outputHTML;
}

function openImage(param_image) {
	var outerDiv = document.createElement("div");
	outerDiv.id = "view-image-div";
	outerDiv.addEventListener("click", removeOpenImage);
	
	
	var popup = document.createElement("div");
	popup.id = "view-image-popup-div";
	
	var image = document.createElement("img");
	image.src = param_image.src;
	image.id = "view-image-popup-div-image";
	
	popup.appendChild(image);
	
	outerDiv.appendChild(popup);
	document.body.appendChild(outerDiv);
}

function removeOpenImage() {
	var element = document.getElementById("view-image-div");
	element.parentNode.removeChild(element);
}