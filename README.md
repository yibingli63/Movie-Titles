Movie Titles

Tp Solve this challenge, write an HTTP GET method to retrieve information from a particular movie database. Complete the function in the editor; it has one parameter: a string, substr. The function must perform the following tasks:

1. Query https://jsonmock.hackerrank.com/api/movies/search/?Title=substr (where substr is the status of substr). They query response from the website is a JSON response with following live fields:
* page: The current page.
* per_page: The maximum number of results per page
* total: The total number of such movies having the substring substr in their title
* total_pages: The total number of pages which must be queried to get all the results.
* data: An array of JSON objects containing movie information where the Titlefield denotes the title of the movie.
