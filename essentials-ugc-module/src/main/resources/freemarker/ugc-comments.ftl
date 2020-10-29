<#include "../include/imports.ftl">

<div class="jumbotron">
    <h3 class="display-6">Comments:</h3>
    <hr/>
    <#list comments as comment>
        <div class="card">
            <div class="card-header">
                ${comment.getName()}
            </div>
            <div class="card-body">
                <#if editMode>
                    <div style="float:right;">
                        <span>state: ${comment.getPublicationState()}</span>
                        <a href="Javascript:top.location='http://localhost:8080/cms/user-generated-content/${comment.getId()}'">edit</a>
                    </div>
                </#if>
                <p class="card-text"> ${comment.getText()}</p>
            </div>
        </div>
    </#list>
</div>

<div class="jumbotron">
    <h3 class="display-6">Place a comment:</h3>
    <hr/>
    <@hst.actionURL var="actionLink"/>
    <form action="${actionLink}" method="post">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Name">
        </div>
        <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email">
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>
        <div class="form-group">
            <label for="text">Text</label>
            <textarea class="form-control" id="text" name="text" rows="3"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>