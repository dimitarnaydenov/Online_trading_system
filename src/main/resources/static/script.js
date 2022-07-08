function addProduct(event){
    let id = parseInt(event.getAttribute("id"));
    let imageURL = event.parentNode.parentNode.parentNode.childNodes[3].src;
    let name = event.parentNode.parentNode.parentNode.childNodes[7].childNodes[1].childNodes[3].getAttribute('value');
    let price = event.parentNode.parentNode.parentNode.childNodes[7].childNodes[1].childNodes[7].getAttribute('value');
    let products = [];
    if(sessionStorage.getItem('products')){
        products = JSON.parse(sessionStorage.getItem('products'));
    }

    if (typeof products[id] !== 'undefined' && products[id] !== null)
    {
        products[id].count++;
    }
    else
    {
        products[id] = ({'productId' : id, name:name, price:price, image : imageURL, count: 1});

    }

    sessionStorage.setItem('products', JSON.stringify(products));
}

async function loadProduct()
{

    let products = [];
    if(sessionStorage.getItem('products')){
        products = JSON.parse(sessionStorage.getItem('products'));
    }
    let allPrice = 0;
    let count = 1;


    for (let element of products) {
        if (element != null)
        {
            let price = 0;

            price = await fun(element, count);

            if (price === 0.0)
            {

                const index = products.indexOf(element);
                products[index] = null;
                sessionStorage.setItem('products', JSON.stringify(products));
                continue
            }

            $("#products").append(`<tr>\n" +
            "                <th scope=\"row\" >${count++}</th>\n" +
            "                <td>${element["name"]}</td>\n" +
            "                <td>${price.toFixed(2)}</td>\n" +
            "                  <td>${element["count"]}</td>\n" +
            "                <td><a class=\"btn btn-danger\" onclick="removeElement(this,${element["productId"]}, ${price},${element["count"]})" href=\"#\">Delete</a></td>\n" +
            "            </tr>`);

            allPrice+= (price * parseInt(element["count"]));
        }
    }

    document.getElementById('price').textContent = allPrice;
}

async function fun(item) {
        return await $.ajax({
            type : "GET",
            url : `getPrice?id=${item['productId']}`,
            success : function(response) {

                return response;
            },
            error : function(e) {
                alert('Error: ' + e);
            }
        });

}

function removeElement(element, id, price,count)
{

    let products = [];
    if(sessionStorage.getItem('products')){
        products = JSON.parse(sessionStorage.getItem('products'));
    }

    for (let element of products) {
        if (element !== null && parseInt(element["productId"]) === parseInt(id)){


            const index = products.indexOf(element);
            products[index] = null;
            // if (index > -1) { // only splice array when item is found
            //     products.splice(index, 1); // 2nd parameter means remove one item only
            // }

        }

    }
    sessionStorage.setItem('products', JSON.stringify(products));


    document.getElementById('price').textContent = parseFloat(document.getElementById('price').textContent) - parseFloat(price)*parseFloat(count);
    element.parentNode.parentNode.remove();


}

function buy()
{
    let products = [];
    if(sessionStorage.getItem('products')){
        products = JSON.parse(sessionStorage.getItem('products'));
    }

    let ids = [];
    let counts = [];

    for (let element of products) {
        if (element !== null){
            ids.push(parseInt(element['productId']));
            counts.push(element['count']);

        }

    }

    $.ajax({
        type : "POST",
        url : "/buy",
        data : {
            ids: ids,
            counts: counts
        },
        success : function(response) {
            sessionStorage.setItem('products', []);
            location.href='/cart?id=success';
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
}