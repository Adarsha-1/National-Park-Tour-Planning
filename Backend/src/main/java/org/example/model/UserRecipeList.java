package org.example.model;

import java.util.List;

public class UserRecipeList {

    public List<RecipeDetails> approvalList;

    public List<RecipeDetails> pendingList;

    public List<RecipeDetails> getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(List<RecipeDetails> approvalList) {
        this.approvalList = approvalList;
    }

    public List<RecipeDetails> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<RecipeDetails> pendingList) {
        this.pendingList = pendingList;
    }

    @Override
    public String toString() {
        return "UserRecipeList{" +
                "approvalList=" + approvalList +
                ", pendingList=" + pendingList +
                '}';
    }
}
