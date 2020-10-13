<#include "../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.example.beans.NewsDocument" -->
<#if document??>
    <@hst.link var="link" hippobean=document/>
    <article class="has-edit-button">
        <@hst.manageContent hippobean=document/>
        <h3><a href="${link}">${document.title?html}</a></h3>
        <#if document.date??>
            <p><@fmt.formatDate value=document.date.time type="both" dateStyle="medium" timeStyle="short"/></p>
        </#if>
        <#if document.endDate??>
            <p><@fmt.formatDate value=document.endDate.time type="both" dateStyle="medium" timeStyle="short"/></p>
        </#if>
        <#if document.author??>
            <p>${document.author?html}</p>
        </#if>
        <#if document.source??>
            <p>${document.source?html}</p>
        </#if>
        <#if document.location??>
            <p>${document.location?html}</p>
        </#if>
        <#if document.introduction??>
            <p>${document.introduction?html}</p>
        </#if>
        <#if document.image?? && document.image.original??>
            <@hst.link var="img" hippobean=document.image.original/>
            <figure>
                <img src="${img}" title="${document.image.fileName?html}" alt="${document.image.fileName?html}"/>
                <#if document.image.description??>
                    <figcaption>${document.image.description?html}</figcaption>
                </#if>
            </figure>
        </#if>
        <@hst.html hippohtml=document.content/>

<#--        <div class="jumbotron">-->
        <#--            <h3 class="display-6">Comments:</h3>-->
        <#--            <hr/>-->
        <#--            <div class="card">-->
        <#--                <div class="card-header">-->
        <#--                    Name-->
        <#--                </div>-->
        <#--                <div class="card-body">-->
        <#--                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>-->
        <#--                </div>-->
        <#--            </div>-->
        <#--            <div class="card">-->
        <#--                <div class="card-header">-->
        <#--                    Name-->
        <#--                </div>-->
        <#--                <div class="card-body">-->
        <#--                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>-->
        <#--                </div>-->
        <#--            </div>-->
        <#--        </div>-->

        <#--        <div class="jumbotron">-->
        <#--            <h3 class="display-6">Place a comment:</h3>-->
        <#--            <hr/>-->
        <#--            <@hst.actionURL var="actionLink"/>-->
        <#--            <form action="${actionLink}" method="post">-->
        <#--                <div class="form-group">-->
        <#--                    <label for="name">Name</label>-->
        <#--                    <input type="text" class="form-control" id="name" name="name" placeholder="Name">-->
        <#--                </div>-->
        <#--                <div class="form-group">-->
        <#--                    <label for="email">Email address</label>-->
        <#--                    <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email">-->
        <#--                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>-->
        <#--                </div>-->
        <#--                <div class="form-group">-->
        <#--                    <label for="text">Text</label>-->
        <#--                    <textarea class="form-control" id="text" name="text" rows="3"></textarea>-->
        <#--                </div>-->
        <#--                <button type="submit" class="btn btn-primary">Submit</button>-->
        <#--            </form>-->
        <#--        </div>-->

    </article>
</#if>