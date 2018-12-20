function countingCharacters(stringToCount){
  //The length property has been mention in

  //the logging letter exercise in lesson 4
  console.log(" ' " + stringToCount + "'" + " has " + stringToCount.length + " characters.");
}
  var characterCount = 0;

function countCharacters(string, character){

  for(var characterPosition = 0;characterPosition<string.length;characterPosition++){
  if(string[characterPosition]== character){
      characterCount++
  }
}console.log("There are " + characterCount + " occurrences of " + character);
}

var sentence =

countCharacters("xxaxxxbxx", "xx");



function rollDice(numSides){

 return Math.floor(Math.random()* numSides) + 1;

}

console.log(rollDice(12));


function addTwoNumbers(firstNumber, secondNumber){
    return firstNumber + secondNumber;

}
document.write(addTwoNumbers(5, 1000));
