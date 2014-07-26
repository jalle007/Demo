//Create meal: parameters Meal object
Parse.Cloud.define("create", function (request, response) {
    var query = new Parse.Query("Meals");
  
    var Meal = Parse.Object.extend("Meals");
    var meal = new Meal();
    meal.set("meal", request.params.meal);
    meal.set("calories", request.params.calories);
    meal.set("user", request.params.user);
    meal.set("date", request.params.date);

    meal.save(null, {
        success: function (mealsaved) {
            console.log("Create succeed");
            response.success("Meal added");
        },
        error: function (error) {
            response.error(error.code + " " + error.message);
        }
 });
});

// Update meal: parameters Meal object
Parse.Cloud.define("update", function (request, response) {
    var query = new Parse.Query("Meals");
    var meal =request.params.meal
  
    query.equalTo("objectId", request.params.objectId);

    query.first({
        success: function(object) {
            object.set("meal", request.params.meal);
            object.set("calories", request.params.calories);
            object.set("user", request.params.user);
            object.set("date", request.params.date);
            object.save();
            response.success("Meal updated");
        },
        error: function(error) {
           response.error( error.code + " " + error.message);
        }
    });
});


//  Select meals, parameters : userId , from , to dates
Parse.Cloud.define("select", function (request, response) {
    var query = new Parse.Query("Meals");
    console.log(request.params.from)

    query.equalTo("user", request.params.user);
    if (request.params.from != null) {
        query.greaterThan("date", request.params.from);
    }         
    if (request.params.to != null)
        query.lessThan("date", request.params.to);
    
    query.find({
        success: function (results) {
           response.success(results);
        },
        error: function () {
          response.error("select failed");
        }
    });
});

// Delete meal, parameters: objectId
Parse.Cloud.define("delete", function (request, response) {
    var query = new Parse.Query("Meals");
    query.equalTo("objectId", request.params.objectid);

    query.find({
        success: function (results) {
            for (var i = 0, len = results.length; i < len; i++) {
                var result = results[i];
                result.destroy();
                console.log("Destroy: " + i);
            }

              response.success("Meal deleted");
        },
        error: function () {
            response.error("deleted failed");
        }
    });
});


// Use Parse.Cloud.define to define as many cloud functions as you want.
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world ");
});


