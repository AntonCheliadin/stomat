<template>
    <div class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>Create doctor</h2>
            </div>

            <div class="row clearfix">
                <form class="needs-validation" novalidate  method="POST">
                    <div class="form-row">
                        <div class="col-md-4 mb-3">
                            <label for="doctorFirstName">global.first.name.label</label>
                            <input type="text" class="form-control" id="doctorFirstName"
                                   placeholder="#{global.first.name.label}" required>
                            <div class="invalid-feedback">
                                global.first.name.empty
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="doctorFathersName" >global.fathers.name.label</label>
                            <input type="text" class="form-control" id="doctorFathersName"
                                   placeholder="#{global.fathers.name.label}" required >
                            <div class="invalid-feedback" >
                                global.fathers.name.empty
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="doctorLastName">global.last.name.label</label>
                            <input type="text" class="form-control" id="doctorLastName"
                                   placeholder="#{global.last.name.label}" required >
                            <div class="invalid-feedback">
                                global.last.name.empty
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="doctorEmail">global.email.label</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend">@</span>
                                </div>
                                <input type="text" class="form-control" id="doctorEmail"
                                       placeholder="#{global.email.label}"
                                       aria-describedby="inputGroupPrepend" required >
                                <div class="invalid-feedback" >
                                    auth.login.email.empty
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="btn btn-primary" type="button">global.save.label</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AddDoctor"
    }
</script>

<style scoped>

</style>