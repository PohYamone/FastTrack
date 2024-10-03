Create Order

```bash
curl -X POST <http://localhost:9090/api/orders> \\
-H "Content-Type: application/json" \\
-d '{
  "customerId": 1,
  "orderItems": [
    {
      "productId": 101,
      "quantity": 2,
      "price": 20.00
    },
    {
      "productId": 102,
      "quantity": 1,
      "price": 50.00
    }
  ]
}'


```

```bash
[
    {
        "id": 1,
        "customerId": 1,
        "status": "PENDING",
        "orderItems": [
            {
                "id": 1,
                "productId": 101,
                "quantity": 2,
                "price": 20
            },
            {
                "id": 2,
                "productId": 102,
                "quantity": 1,
                "price": 50
            }
        ]
    }
]

```

Get Order by ID

```bash
curl -X GET <http://localhost:9090/api/orders/1>

```

```bash
{
    "id": 1,
    "customerId": 1,
    "status": "PENDING",
    "orderItems": [
        {
            "id": 1,
            "productId": 101,
            "quantity": 2,
            "price": 20
        },
        {
            "id": 2,
            "productId": 102,
            "quantity": 1,
            "price": 50
        }
    ]
}

```

Get Orders by Customer ID

```bash
curl -X GET <http://localhost:9090/api/orders/customers/1>

```

```bash
{
    "id": 1,
    "customerId": 1,
    "status": "PENDING",
    "orderItems": [
        {
            "id": 1,
            "productId": 101,
            "quantity": 2,
            "price": 20
        },
        {
            "id": 2,
            "productId": 102,
            "quantity": 1,
            "price": 50
        }
    ]
}

```

Get All Orders

```bash
curl -X GET <http://localhost:9090/api/orders>

```

```bash
{
    "id": 1,
    "customerId": 1,
    "status": "PENDING",
    "orderItems": [
        {
            "id": 1,
            "productId": 101,
            "quantity": 2,
            "price": 20
        },
        {
            "id": 2,
            "productId": 102,
            "quantity": 1,
            "price": 50
        }
    ]
}

```

Update Order Status

```bash
curl -X PATCH <http://localhost:9090/api/orders/1/status> \\
-d "status=CONFIRMED"

```

```bash
{
    "id": 1,
    "customerId": 1,
    "status": "CONFIRMED",
    "orderItems": [
        {
            "id": 1,
            "productId": 101,
            "quantity": 2,
            "price": 20
        },
        {
            "id": 2,
            "productId": 102,
            "quantity": 1,
            "price": 50
        }
    ]
}

```

Get Order Items by Order ID

```bash
curl -X GET <http://localhost:9090/api/orders/1/items>

```

```bash
[
    {
        "id": 1,
        "productId": 101,
        "quantity": 2,
        "price": 20
    },
    {
        "id": 2,
        "productId": 102,
        "quantity": 1,
        "price": 50
    }
]

```

Add Item to Order

```bash
curl -X POST <http://localhost:9090/api/orders/1/items> \\
-H "Content-Type: application/json" \\
-d '{
  "productId": 103,
  "quantity": 1,
  "price": 30.00
}'


```

```bash
{
    "id": 1,
    "customerId": 1,
    "status": "CONFIRMED",
    "orderItems": [
        {
            "id": 1,
            "productId": 101,
            "quantity": 2,
            "price": 20
        },
        {
            "id": 2,
            "productId": 102,
            "quantity": 1,
            "price": 50
        },
        {
            "id": 5,
            "productId": 103,
            "quantity": 1,
            "price": 30
        }
    ]
}

```

Remove Item from Order

```bash
curl -X DELETE <http://localhost:9090/api/orders/1/items/3>
```

Delete Order

```bash
curl -X DELETE <http://localhost:9090/api/orders/1>
```
