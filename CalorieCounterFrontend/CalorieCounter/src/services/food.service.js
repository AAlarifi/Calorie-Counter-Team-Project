const submitCalories = (food, calories) => {

    return fetch("http://localhost:8088/food?name=" + food + "&calories=" + calories, {
        method: "POST"
    })
    .then((response) => {
        if(response.status === 201) {
            return response.text();
        }
    })

}

// Exports functions
export const foodService = {
    submitCalories
}