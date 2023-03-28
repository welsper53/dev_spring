const repeatWidth = (character, count) => character.repeat(count);
console.log(repeatWidth('*', 5))

const repeatWidth2 = (character) => (count) => character.repeat(count);
console.log(repeatWidth2('$')(3))

const repeatStar = repeatWidth2('*')
console.log(repeatStar(3))