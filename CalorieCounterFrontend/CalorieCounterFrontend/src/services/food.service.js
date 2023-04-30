const submitCalories = (food, calories) => {

    return fetch(`http://localhost:8008/food?name=${food}&calories=${calories}`, {
        method: "POST"
    })
        .then((response) => {
            if (response.status === 200) {
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
            if (response.status === 200) {
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

    return fetch(`http://localhost:8008/food/search?query=${encodedSearch}`, {
        method: "GET"
    })
        .then((response) => {
            if (response.status === 200) {
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

const searchParser = (result) => {

    if (!result) {
        return Promise.reject("result is empty or undefined");
    }
    const encodedSearch = encodeURIComponent(result);

    return fetch(`http://localhost:8008/food/searchParser?query=${encodedSearch}`, {
        method: "GET"
    })
        .then((response) => {
            if (response.status === 200) {
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

const foodInformation = (foodData) => {
    const {NOS, foodId, measurements} = foodData

    // if (!NOS || !servingSize || !foodId || !measurements) {
    //     return Promise.reject("Number of servings, serving size, or food ID is empty or undefined");
    // }
    // if (!servingSize) {
    //     return Promise.reject(" serving size error");
    // }
    if ( !foodId ) {
        return Promise.reject("food id missing");
    }
    if (!measurements) {
        return Promise.reject("measurement errror");
    }

    if (!NOS) {
        return Promise.reject("nos errror");
    }

    let measurementsOption;
    switch (measurements) {
        case 'ml':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_milliliter';
            break;
        case 'gram':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_gram';
            break;
        case 'ounce':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_ounce';
            break;
        case 'pound':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_pound';
            break;
        case 'kilogram':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram';
            break;
        case 'cup':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_cup';
            break;
        case 'serving':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_serving';
            break;
        default:
            throw new Error('Invalid measurement level');
    }

    const requestBody = new URLSearchParams({
        quantity: NOS,
        measureURI: measurementsOption,
        foodId: foodId
    });

    return fetch(`http://localhost:8008/food/searchRequest`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: requestBody
    })
        .then((response) => {
            if (response.status === 200) {
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
    searchFood: searchFood,
    searchParser: searchParser,
    foodInformation: foodInformation
}