$(function() {
	
  var item =  [ [(${itemListForAutocomplete})] ];

  $( "#searchName" ).autocomplete({
    source: item
  });
});