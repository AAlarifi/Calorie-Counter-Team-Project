const submitCalories = (food, calories) => {

    return fetch("http://localhost:8088/food?name=" + food + "&calories=" + calories, {
        method: "POST"
    })
    .then((response) => {
        if(response.status === 200) {
            return response.text();
        }
        else {
            throw "Something went wrong"
        }
    })
    .then((res) => {
        return res
    })
    .catch((error) => {
        console.log("err", error)
        return Promise.reject(error)
    })


}

// Exports functions
export default {
    submitCalories: submitCalories
}