const submitCalories = (food, calories) => {

    return fetch("http://localhost:8088/food?name=" + food + "&calories=" + calories, {
        method: "POST",
        
    })

}