const submitCalories = (food, calories) => {

    return fetch(`http://localhost:8008/food?name=${food}&calories=${calories}`, {
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

const foodCaloriesCalc = () => {

    return fetch(`http://localhost:8008/foodCalories`, {
        method: "GET"
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

const searchFood = (search) => {

    if (!search) {
        return Promise.reject("Search string is empty or undefined");
      }
    const encodedSearch = encodeURIComponent(search);
    
    return fetch(`http://localhost:8008/food/search?query=${encodedSearch}` , {
        method: "GET"
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
    submitCalories: submitCalories,
    foodCaloriesCalc: foodCaloriesCalc,
    searchFood: searchFood
}